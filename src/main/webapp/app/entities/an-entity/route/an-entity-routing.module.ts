import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AnEntityComponent } from '../list/an-entity.component';
import { AnEntityDetailComponent } from '../detail/an-entity-detail.component';
import { AnEntityUpdateComponent } from '../update/an-entity-update.component';
import { AnEntityRoutingResolveService } from './an-entity-routing-resolve.service';

const anEntityRoute: Routes = [
  {
    path: '',
    component: AnEntityComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnEntityDetailComponent,
    resolve: {
      anEntity: AnEntityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnEntityUpdateComponent,
    resolve: {
      anEntity: AnEntityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnEntityUpdateComponent,
    resolve: {
      anEntity: AnEntityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(anEntityRoute)],
  exports: [RouterModule],
})
export class AnEntityRoutingModule {}
