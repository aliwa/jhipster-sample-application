import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IA, A } from '../a.model';
import { AService } from '../service/a.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-a-update',
  templateUrl: './a-update.component.html',
})
export class AUpdateComponent implements OnInit {
  isSaving = false;

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
    protected aService: AService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ a }) => {
      if (a.id === undefined) {
        const today = dayjs().startOf('day');
        a.aZonedDateTime = today;
        a.aInstant = today;
      }

      this.updateForm(a);
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
    const a = this.createFromForm();
    if (a.id !== undefined) {
      this.subscribeToSaveResponse(this.aService.update(a));
    } else {
      this.subscribeToSaveResponse(this.aService.create(a));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IA>>): void {
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

  protected updateForm(a: IA): void {
    this.editForm.patchValue({
      id: a.id,
      aString: a.aString,
      aInteger: a.aInteger,
      aLong: a.aLong,
      aBigDecimal: a.aBigDecimal,
      aFloat: a.aFloat,
      aDouble: a.aDouble,
      aEnum: a.aEnum,
      aBoolean: a.aBoolean,
      aLocalDate: a.aLocalDate,
      aZonedDateTime: a.aZonedDateTime ? a.aZonedDateTime.format(DATE_TIME_FORMAT) : null,
      aInstant: a.aInstant ? a.aInstant.format(DATE_TIME_FORMAT) : null,
      aDuration: a.aDuration,
      aUUID: a.aUUID,
      aBlob: a.aBlob,
      aBlobContentType: a.aBlobContentType,
      aAnyBlob: a.aAnyBlob,
      aAnyBlobContentType: a.aAnyBlobContentType,
      imageBlob: a.imageBlob,
      imageBlobContentType: a.imageBlobContentType,
      aTextBlob: a.aTextBlob,
    });
  }

  protected createFromForm(): IA {
    return {
      ...new A(),
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
