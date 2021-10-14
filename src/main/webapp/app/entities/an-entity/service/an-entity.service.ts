import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnEntity, getAnEntityIdentifier } from '../an-entity.model';

export type EntityResponseType = HttpResponse<IAnEntity>;
export type EntityArrayResponseType = HttpResponse<IAnEntity[]>;

@Injectable({ providedIn: 'root' })
export class AnEntityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/an-entities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(anEntity: IAnEntity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anEntity);
    return this.http
      .post<IAnEntity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(anEntity: IAnEntity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anEntity);
    return this.http
      .put<IAnEntity>(`${this.resourceUrl}/${getAnEntityIdentifier(anEntity) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(anEntity: IAnEntity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anEntity);
    return this.http
      .patch<IAnEntity>(`${this.resourceUrl}/${getAnEntityIdentifier(anEntity) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAnEntityToCollectionIfMissing(anEntityCollection: IAnEntity[], ...anEntitiesToCheck: (IAnEntity | null | undefined)[]): IAnEntity[] {
    const anEntities: IAnEntity[] = anEntitiesToCheck.filter(isPresent);
    if (anEntities.length > 0) {
      const anEntityCollectionIdentifiers = anEntityCollection.map(anEntityItem => getAnEntityIdentifier(anEntityItem)!);
      const anEntitiesToAdd = anEntities.filter(anEntityItem => {
        const anEntityIdentifier = getAnEntityIdentifier(anEntityItem);
        if (anEntityIdentifier == null || anEntityCollectionIdentifiers.includes(anEntityIdentifier)) {
          return false;
        }
        anEntityCollectionIdentifiers.push(anEntityIdentifier);
        return true;
      });
      return [...anEntitiesToAdd, ...anEntityCollection];
    }
    return anEntityCollection;
  }

  protected convertDateFromClient(anEntity: IAnEntity): IAnEntity {
    return Object.assign({}, anEntity, {
      aLocalDate: anEntity.aLocalDate?.isValid() ? anEntity.aLocalDate.format(DATE_FORMAT) : undefined,
      aZonedDateTime: anEntity.aZonedDateTime?.isValid() ? anEntity.aZonedDateTime.toJSON() : undefined,
      aInstant: anEntity.aInstant?.isValid() ? anEntity.aInstant.toJSON() : undefined,
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
      res.body.forEach((anEntity: IAnEntity) => {
        anEntity.aLocalDate = anEntity.aLocalDate ? dayjs(anEntity.aLocalDate) : undefined;
        anEntity.aZonedDateTime = anEntity.aZonedDateTime ? dayjs(anEntity.aZonedDateTime) : undefined;
        anEntity.aInstant = anEntity.aInstant ? dayjs(anEntity.aInstant) : undefined;
      });
    }
    return res;
  }
}
