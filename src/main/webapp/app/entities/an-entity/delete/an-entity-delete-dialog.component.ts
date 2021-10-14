import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnEntity } from '../an-entity.model';
import { AnEntityService } from '../service/an-entity.service';

@Component({
  templateUrl: './an-entity-delete-dialog.component.html',
})
export class AnEntityDeleteDialogComponent {
  anEntity?: IAnEntity;

  constructor(protected anEntityService: AnEntityService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anEntityService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
