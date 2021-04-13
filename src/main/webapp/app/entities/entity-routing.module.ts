import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'entity-test',
        data: { pageTitle: 'myApp.entityTest.home.title' },
        loadChildren: () => import('./entity-test/entity-test.module').then(m => m.EntityTestModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
