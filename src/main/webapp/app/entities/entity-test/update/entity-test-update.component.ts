import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEntityTest, EntityTest } from '../entity-test.model';
import { EntityTestService } from '../service/entity-test.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-entity-test-update',
  templateUrl: './entity-test-update.component.html',
})
export class EntityTestUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    aString: [
      null,
      [Validators.required, Validators.minLength(10), Validators.maxLength(100), Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')],
    ],
    aInteger: [null, [Validators.required, Validators.min(10), Validators.max(100)]],
    aLong: [null, [Validators.required, Validators.min(10), Validators.max(100)]],
    aBigDecimal: [null, [Validators.required, Validators.min(10), Validators.max(100)]],
    aFloat: [null, [Validators.required, Validators.min(10), Validators.max(100)]],
    aDouble: [null, [Validators.required, Validators.min(10), Validators.max(100)]],
    aBoolean: [null, [Validators.required]],
    aLocalDate: [null, [Validators.required]],
    aZonedDateTime: [null, [Validators.required]],
    aInstant: [null, [Validators.required]],
    aDuration: [null, [Validators.required]],
    aUUID: [null, [Validators.required]],
    aBlob: [null, [Validators.required]],
    aBlobContentType: [],
    aAnyBlob: [null, [Validators.required]],
    aAnyBlobContentType: [],
    aImageBlob: [null, [Validators.required]],
    aImageBlobContentType: [],
    aTextBlob: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected entityTestService: EntityTestService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityTest }) => {
      if (entityTest.id === undefined) {
        const today = dayjs().startOf('day');
        entityTest.aZonedDateTime = today;
        entityTest.aInstant = today;
      }

      this.updateForm(entityTest);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('myApp.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entityTest = this.createFromForm();
    if (entityTest.id !== undefined) {
      this.subscribeToSaveResponse(this.entityTestService.update(entityTest));
    } else {
      this.subscribeToSaveResponse(this.entityTestService.create(entityTest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntityTest>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(entityTest: IEntityTest): void {
    this.editForm.patchValue({
      id: entityTest.id,
      aString: entityTest.aString,
      aInteger: entityTest.aInteger,
      aLong: entityTest.aLong,
      aBigDecimal: entityTest.aBigDecimal,
      aFloat: entityTest.aFloat,
      aDouble: entityTest.aDouble,
      aBoolean: entityTest.aBoolean,
      aLocalDate: entityTest.aLocalDate,
      aZonedDateTime: entityTest.aZonedDateTime ? entityTest.aZonedDateTime.format(DATE_TIME_FORMAT) : null,
      aInstant: entityTest.aInstant ? entityTest.aInstant.format(DATE_TIME_FORMAT) : null,
      aDuration: entityTest.aDuration,
      aUUID: entityTest.aUUID,
      aBlob: entityTest.aBlob,
      aBlobContentType: entityTest.aBlobContentType,
      aAnyBlob: entityTest.aAnyBlob,
      aAnyBlobContentType: entityTest.aAnyBlobContentType,
      aImageBlob: entityTest.aImageBlob,
      aImageBlobContentType: entityTest.aImageBlobContentType,
      aTextBlob: entityTest.aTextBlob,
    });
  }

  protected createFromForm(): IEntityTest {
    return {
      ...new EntityTest(),
      id: this.editForm.get(['id'])!.value,
      aString: this.editForm.get(['aString'])!.value,
      aInteger: this.editForm.get(['aInteger'])!.value,
      aLong: this.editForm.get(['aLong'])!.value,
      aBigDecimal: this.editForm.get(['aBigDecimal'])!.value,
      aFloat: this.editForm.get(['aFloat'])!.value,
      aDouble: this.editForm.get(['aDouble'])!.value,
      aBoolean: this.editForm.get(['aBoolean'])!.value,
      aLocalDate: this.editForm.get(['aLocalDate'])!.value,
      aZonedDateTime: this.editForm.get(['aZonedDateTime'])!.value
        ? dayjs(this.editForm.get(['aZonedDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      aInstant: this.editForm.get(['aInstant'])!.value ? dayjs(this.editForm.get(['aInstant'])!.value, DATE_TIME_FORMAT) : undefined,
      aDuration: this.editForm.get(['aDuration'])!.value,
      aUUID: this.editForm.get(['aUUID'])!.value,
      aBlobContentType: this.editForm.get(['aBlobContentType'])!.value,
      aBlob: this.editForm.get(['aBlob'])!.value,
      aAnyBlobContentType: this.editForm.get(['aAnyBlobContentType'])!.value,
      aAnyBlob: this.editForm.get(['aAnyBlob'])!.value,
      aImageBlobContentType: this.editForm.get(['aImageBlobContentType'])!.value,
      aImageBlob: this.editForm.get(['aImageBlob'])!.value,
      aTextBlob: this.editForm.get(['aTextBlob'])!.value,
    };
  }
}
