import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EntityTestComponent } from '../list/entity-test.component';
import { EntityTestDetailComponent } from '../detail/entity-test-detail.component';
import { EntityTestUpdateComponent } from '../update/entity-test-update.component';
import { EntityTestRoutingResolveService } from './entity-test-routing-resolve.service';

const entityTestRoute: Routes = [
  {
    path: '',
    component: EntityTestComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EntityTestDetailComponent,
    resolve: {
      entityTest: EntityTestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EntityTestUpdateComponent,
    resolve: {
      entityTest: EntityTestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EntityTestUpdateComponent,
    resolve: {
      entityTest: EntityTestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(entityTestRoute)],
  exports: [RouterModule],
})
export class EntityTestRoutingModule {}
