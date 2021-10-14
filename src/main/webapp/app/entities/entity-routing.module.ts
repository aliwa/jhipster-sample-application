import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'an-entity',
        data: { pageTitle: 'myApp.anEntity.home.title' },
        loadChildren: () => import('./an-entity/an-entity.module').then(m => m.AnEntityModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
