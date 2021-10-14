import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AnEntityComponent } from './list/an-entity.component';
import { AnEntityDetailComponent } from './detail/an-entity-detail.component';
import { AnEntityUpdateComponent } from './update/an-entity-update.component';
import { AnEntityDeleteDialogComponent } from './delete/an-entity-delete-dialog.component';
import { AnEntityRoutingModule } from './route/an-entity-routing.module';

@NgModule({
  imports: [SharedModule, AnEntityRoutingModule],
  declarations: [AnEntityComponent, AnEntityDetailComponent, AnEntityUpdateComponent, AnEntityDeleteDialogComponent],
  entryComponents: [AnEntityDeleteDialogComponent],
})
export class AnEntityModule {}
