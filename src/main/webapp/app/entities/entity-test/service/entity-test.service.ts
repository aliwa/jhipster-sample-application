import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEntityTest, getEntityTestIdentifier } from '../entity-test.model';

export type EntityResponseType = HttpResponse<IEntityTest>;
export type EntityArrayResponseType = HttpResponse<IEntityTest[]>;

@Injectable({ providedIn: 'root' })
export class EntityTestService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/entity-tests');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(entityTest: IEntityTest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entityTest);
    return this.http
      .post<IEntityTest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(entityTest: IEntityTest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entityTest);
    return this.http
      .put<IEntityTest>(`${this.resourceUrl}/${getEntityTestIdentifier(entityTest) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(entityTest: IEntityTest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entityTest);
    return this.http
      .patch<IEntityTest>(`${this.resourceUrl}/${getEntityTestIdentifier(entityTest) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEntityTest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEntityTest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEntityTestToCollectionIfMissing(
    entityTestCollection: IEntityTest[],
    ...entityTestsToCheck: (IEntityTest | null | undefined)[]
  ): IEntityTest[] {
    const entityTests: IEntityTest[] = entityTestsToCheck.filter(isPresent);
    if (entityTests.length > 0) {
      const entityTestCollectionIdentifiers = entityTestCollection.map(entityTestItem => getEntityTestIdentifier(entityTestItem)!);
      const entityTestsToAdd = entityTests.filter(entityTestItem => {
        const entityTestIdentifier = getEntityTestIdentifier(entityTestItem);
        if (entityTestIdentifier == null || entityTestCollectionIdentifiers.includes(entityTestIdentifier)) {
          return false;
        }
        entityTestCollectionIdentifiers.push(entityTestIdentifier);
        return true;
      });
      return [...entityTestsToAdd, ...entityTestCollection];
    }
    return entityTestCollection;
  }

  protected convertDateFromClient(entityTest: IEntityTest): IEntityTest {
    return Object.assign({}, entityTest, {
      aLocalDate: entityTest.aLocalDate?.isValid() ? entityTest.aLocalDate.format(DATE_FORMAT) : undefined,
      aZonedDateTime: entityTest.aZonedDateTime?.isValid() ? entityTest.aZonedDateTime.toJSON() : undefined,
      aInstant: entityTest.aInstant?.isValid() ? entityTest.aInstant.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.aLocalDate = res.body.aLocalDate ? dayjs(res.body.aLocalDate) : undefined;
      res.body.aZonedDateTime = res.body.aZonedDateTime ? dayjs(res.body.aZonedDateTime) : undefined;
      res.body.aInstant = res.body.aInstant ? dayjs(res.body.aInstant) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((entityTest: IEntityTest) => {
        entityTest.aLocalDate = entityTest.aLocalDate ? dayjs(entityTest.aLocalDate) : undefined;
        entityTest.aZonedDateTime = entityTest.aZonedDateTime ? dayjs(entityTest.aZonedDateTime) : undefined;
        entityTest.aInstant = entityTest.aInstant ? dayjs(entityTest.aInstant) : undefined;
      });
    }
    return res;
  }
}
