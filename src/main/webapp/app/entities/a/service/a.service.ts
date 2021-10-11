import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IA, getAIdentifier } from '../a.model';

export type EntityResponseType = HttpResponse<IA>;
export type EntityArrayResponseType = HttpResponse<IA[]>;

@Injectable({ providedIn: 'root' })
export class AService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/as');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(a: IA): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(a);
    return this.http
      .post<IA>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(a: IA): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(a);
    return this.http
      .put<IA>(`${this.resourceUrl}/${getAIdentifier(a) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(a: IA): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(a);
    return this.http
      .patch<IA>(`${this.resourceUrl}/${getAIdentifier(a) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IA>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IA[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAToCollectionIfMissing(aCollection: IA[], ...aSToCheck: (IA | null | undefined)[]): IA[] {
    const aS: IA[] = aSToCheck.filter(isPresent);
    if (aS.length > 0) {
      const aCollectionIdentifiers = aCollection.map(aItem => getAIdentifier(aItem)!);
      const aSToAdd = aS.filter(aItem => {
        const aIdentifier = getAIdentifier(aItem);
        if (aIdentifier == null || aCollectionIdentifiers.includes(aIdentifier)) {
          return false;
        }
        aCollectionIdentifiers.push(aIdentifier);
        return true;
      });
      return [...aSToAdd, ...aCollection];
    }
    return aCollection;
  }

  protected convertDateFromClient(a: IA): IA {
    return Object.assign({}, a, {
      aLocalDate: a.aLocalDate?.isValid() ? a.aLocalDate.format(DATE_FORMAT) : undefined,
      aZonedDateTime: a.aZonedDateTime?.isValid() ? a.aZonedDateTime.toJSON() : undefined,
      aInstant: a.aInstant?.isValid() ? a.aInstant.toJSON() : undefined,
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
      res.body.forEach((a: IA) => {
        a.aLocalDate = a.aLocalDate ? dayjs(a.aLocalDate) : undefined;
        a.aZonedDateTime = a.aZonedDateTime ? dayjs(a.aZonedDateTime) : undefined;
        a.aInstant = a.aInstant ? dayjs(a.aInstant) : undefined;
      });
    }
    return res;
  }
}
