import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnEntity } from '../an-entity.model';
import { AnEntityService } from '../service/an-entity.service';
import { AnEntityDeleteDialogComponent } from '../delete/an-entity-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-an-entity',
  templateUrl: './an-entity.component.html',
})
export class AnEntityComponent implements OnInit {
  anEntities?: IAnEntity[];
  isLoading = false;

  constructor(protected anEntityService: AnEntityService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.anEntityService.query().subscribe(
      (res: HttpResponse<IAnEntity[]>) => {
        this.isLoading = false;
        this.anEntities = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAnEntity): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(anEntity: IAnEntity): void {
    const modalRef = this.modalService.open(AnEntityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.anEntity = anEntity;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
