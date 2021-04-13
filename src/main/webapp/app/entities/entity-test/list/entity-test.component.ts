import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntityTest } from '../entity-test.model';
import { EntityTestService } from '../service/entity-test.service';
import { EntityTestDeleteDialogComponent } from '../delete/entity-test-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-entity-test',
  templateUrl: './entity-test.component.html',
})
export class EntityTestComponent implements OnInit {
  entityTests?: IEntityTest[];
  isLoading = false;

  constructor(protected entityTestService: EntityTestService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.entityTestService.query().subscribe(
      (res: HttpResponse<IEntityTest[]>) => {
        this.isLoading = false;
        this.entityTests = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEntityTest): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(entityTest: IEntityTest): void {
    const modalRef = this.modalService.open(EntityTestDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.entityTest = entityTest;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
