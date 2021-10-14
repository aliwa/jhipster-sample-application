import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnEntity, AnEntity } from '../an-entity.model';
import { AnEntityService } from '../service/an-entity.service';

@Injectable({ providedIn: 'root' })
export class AnEntityRoutingResolveService implements Resolve<IAnEntity> {
  constructor(protected service: AnEntityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnEntity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((anEntity: HttpResponse<AnEntity>) => {
          if (anEntity.body) {
            return of(anEntity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AnEntity());
  }
}
