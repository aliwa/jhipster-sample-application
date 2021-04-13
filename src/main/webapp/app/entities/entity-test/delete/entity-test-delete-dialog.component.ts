import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEntityTest } from '../entity-test.model';
import { EntityTestService } from '../service/entity-test.service';

@Component({
  templateUrl: './entity-test-delete-dialog.component.html',
})
export class EntityTestDeleteDialogComponent {
  entityTest?: IEntityTest;

  constructor(protected entityTestService: EntityTestService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entityTestService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
