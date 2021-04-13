import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEntityTest, EntityTest } from '../entity-test.model';
import { EntityTestService } from '../service/entity-test.service';

@Injectable({ providedIn: 'root' })
export class EntityTestRoutingResolveService implements Resolve<IEntityTest> {
  constructor(protected service: EntityTestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntityTest> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((entityTest: HttpResponse<EntityTest>) => {
          if (entityTest.body) {
            return of(entityTest.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EntityTest());
  }
}
