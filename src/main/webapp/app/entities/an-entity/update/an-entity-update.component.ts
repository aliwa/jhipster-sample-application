import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAnEntity, AnEntity } from '../an-entity.model';
import { AnEntityService } from '../service/an-entity.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { AEnum } from 'app/entities/enumerations/a-enum.model';

@Component({
  selector: 'jhi-an-entity-update',
  templateUrl: './an-entity-update.component.html',
})
export class AnEntityUpdateComponent implements OnInit {
  isSaving = false;
  aEnumValues = Object.keys(AEnum);

  editForm = this.fb.group({
    id: [],
    aString: [null, [Validators.required, Validators.minLength(100), Validators.maxLength(1000), Validators.pattern('^[A-Z][a-z]+\\d$')]],
    aInteger: [null, [Validators.required, Validators.min(100), Validators.max(1000)]],
    aLong: [null, [Validators.required, Validators.min(100), Validators.max(1000)]],
    aBigDecimal: [null, [Validators.required, Validators.min(100), Validators.max(1000)]],
    aFloat: [null, [Validators.required, Validators.min(100), Validators.max(1000)]],
    aDouble: [null, [Validators.required, Validators.min(100), Validators.max(1000)]],
    aEnum: [null, [Validators.required]],
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
    imageBlob: [null, [Validators.required]],
    imageBlobContentType: [],
    aTextBlob: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected anEntityService: AnEntityService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anEntity }) => {
      if (anEntity.id === undefined) {
        const today = dayjs().startOf('day');
        anEntity.aZonedDateTime = today;
        anEntity.aInstant = today;
      }

      this.updateForm(anEntity);
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
        this.eventManager.broadcast(new EventWithContent<AlertError>('myApp.error', { ...err, key: 'error.file.' + err.key })),
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
    const anEntity = this.createFromForm();
    if (anEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.anEntityService.update(anEntity));
    } else {
      this.subscribeToSaveResponse(this.anEntityService.create(anEntity));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnEntity>>): void {
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

  protected updateForm(anEntity: IAnEntity): void {
    this.editForm.patchValue({
      id: anEntity.id,
      aString: anEntity.aString,
      aInteger: anEntity.aInteger,
      aLong: anEntity.aLong,
      aBigDecimal: anEntity.aBigDecimal,
      aFloat: anEntity.aFloat,
      aDouble: anEntity.aDouble,
      aEnum: anEntity.aEnum,
      aBoolean: anEntity.aBoolean,
      aLocalDate: anEntity.aLocalDate,
      aZonedDateTime: anEntity.aZonedDateTime ? anEntity.aZonedDateTime.format(DATE_TIME_FORMAT) : null,
      aInstant: anEntity.aInstant ? anEntity.aInstant.format(DATE_TIME_FORMAT) : null,
      aDuration: anEntity.aDuration,
      aUUID: anEntity.aUUID,
      aBlob: anEntity.aBlob,
      aBlobContentType: anEntity.aBlobContentType,
      aAnyBlob: anEntity.aAnyBlob,
      aAnyBlobContentType: anEntity.aAnyBlobContentType,
      imageBlob: anEntity.imageBlob,
      imageBlobContentType: anEntity.imageBlobContentType,
      aTextBlob: anEntity.aTextBlob,
    });
  }

  protected createFromForm(): IAnEntity {
    return {
      ...new AnEntity(),
      id: this.editForm.get(['id'])!.value,
      aString: this.editForm.get(['aString'])!.value,
      aInteger: this.editForm.get(['aInteger'])!.value,
      aLong: this.editForm.get(['aLong'])!.value,
      aBigDecimal: this.editForm.get(['aBigDecimal'])!.value,
      aFloat: this.editForm.get(['aFloat'])!.value,
      aDouble: this.editForm.get(['aDouble'])!.value,
      aEnum: this.editForm.get(['aEnum'])!.value,
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
      imageBlobContentType: this.editForm.get(['imageBlobContentType'])!.value,
      imageBlob: this.editForm.get(['imageBlob'])!.value,
      aTextBlob: this.editForm.get(['aTextBlob'])!.value,
    };
  }
}
