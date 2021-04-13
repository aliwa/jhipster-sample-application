import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EntityTestComponent } from './list/entity-test.component';
import { EntityTestDetailComponent } from './detail/entity-test-detail.component';
import { EntityTestUpdateComponent } from './update/entity-test-update.component';
import { EntityTestDeleteDialogComponent } from './delete/entity-test-delete-dialog.component';
import { EntityTestRoutingModule } from './route/entity-test-routing.module';

@NgModule({
  imports: [SharedModule, EntityTestRoutingModule],
  declarations: [EntityTestComponent, EntityTestDetailComponent, EntityTestUpdateComponent, EntityTestDeleteDialogComponent],
  entryComponents: [EntityTestDeleteDialogComponent],
})
export class EntityTestModule {}
