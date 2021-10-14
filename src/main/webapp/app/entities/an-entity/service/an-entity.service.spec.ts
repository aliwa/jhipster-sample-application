import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { AEnum } from 'app/entities/enumerations/a-enum.model';
import { IAnEntity, AnEntity } from '../an-entity.model';

import { AnEntityService } from './an-entity.service';

describe('AnEntity Service', () => {
  let service: AnEntityService;
  let httpMock: HttpTestingController;
  let elemDefault: IAnEntity;
  let expectedResult: IAnEntity | IAnEntity[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AnEntityService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      aString: 'AAAAAAA',
      aInteger: 0,
      aLong: 0,
      aBigDecimal: 0,
      aFloat: 0,
      aDouble: 0,
      aEnum: AEnum.FRENCH,
      aBoolean: false,
      aLocalDate: currentDate,
      aZonedDateTime: currentDate,
      aInstant: currentDate,
      aDuration: 'PT1S',
      aUUID: 'AAAAAAA',
      aBlobContentType: 'image/png',
      aBlob: 'AAAAAAA',
      aAnyBlobContentType: 'image/png',
      aAnyBlob: 'AAAAAAA',
      imageBlobContentType: 'image/png',
      imageBlob: 'AAAAAAA',
      aTextBlob: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          aLocalDate: currentDate.format(DATE_FORMAT),
          aZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
          aInstant: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a AnEntity', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          aLocalDate: currentDate.format(DATE_FORMAT),
          aZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
          aInstant: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          aLocalDate: currentDate,
          aZonedDateTime: currentDate,
          aInstant: currentDate,
        },
        returnedFromService
      );

      service.create(new AnEntity()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnEntity', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          aString: 'BBBBBB',
          aInteger: 1,
          aLong: 1,
          aBigDecimal: 1,
          aFloat: 1,
          aDouble: 1,
          aEnum: 'BBBBBB',
          aBoolean: true,
          aLocalDate: currentDate.format(DATE_FORMAT),
          aZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
          aInstant: currentDate.format(DATE_TIME_FORMAT),
          aDuration: 'BBBBBB',
          aUUID: 'BBBBBB',
          aBlob: 'BBBBBB',
          aAnyBlob: 'BBBBBB',
          imageBlob: 'BBBBBB',
          aTextBlob: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          aLocalDate: currentDate,
          aZonedDateTime: currentDate,
          aInstant: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnEntity', () => {
      const patchObject = Object.assign(
        {
          aLong: 1,
          aBoolean: true,
          aZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
          aInstant: currentDate.format(DATE_TIME_FORMAT),
          aUUID: 'BBBBBB',
          imageBlob: 'BBBBBB',
        },
        new AnEntity()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          aLocalDate: currentDate,
          aZonedDateTime: currentDate,
          aInstant: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnEntity', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          aString: 'BBBBBB',
          aInteger: 1,
          aLong: 1,
          aBigDecimal: 1,
          aFloat: 1,
          aDouble: 1,
          aEnum: 'BBBBBB',
          aBoolean: true,
          aLocalDate: currentDate.format(DATE_FORMAT),
          aZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
          aInstant: currentDate.format(DATE_TIME_FORMAT),
          aDuration: 'BBBBBB',
          aUUID: 'BBBBBB',
          aBlob: 'BBBBBB',
          aAnyBlob: 'BBBBBB',
          imageBlob: 'BBBBBB',
          aTextBlob: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          aLocalDate: currentDate,
          aZonedDateTime: currentDate,
          aInstant: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a AnEntity', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAnEntityToCollectionIfMissing', () => {
      it('should add a AnEntity to an empty array', () => {
        const anEntity: IAnEntity = { id: 123 };
        expectedResult = service.addAnEntityToCollectionIfMissing([], anEntity);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anEntity);
      });

      it('should not add a AnEntity to an array that contains it', () => {
        const anEntity: IAnEntity = { id: 123 };
        const anEntityCollection: IAnEntity[] = [
          {
            ...anEntity,
          },
          { id: 456 },
        ];
        expectedResult = service.addAnEntityToCollectionIfMissing(anEntityCollection, anEntity);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnEntity to an array that doesn't contain it", () => {
        const anEntity: IAnEntity = { id: 123 };
        const anEntityCollection: IAnEntity[] = [{ id: 456 }];
        expectedResult = service.addAnEntityToCollectionIfMissing(anEntityCollection, anEntity);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anEntity);
      });

      it('should add only unique AnEntity to an array', () => {
        const anEntityArray: IAnEntity[] = [{ id: 123 }, { id: 456 }, { id: 13492 }];
        const anEntityCollection: IAnEntity[] = [{ id: 123 }];
        expectedResult = service.addAnEntityToCollectionIfMissing(anEntityCollection, ...anEntityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anEntity: IAnEntity = { id: 123 };
        const anEntity2: IAnEntity = { id: 456 };
        expectedResult = service.addAnEntityToCollectionIfMissing([], anEntity, anEntity2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anEntity);
        expect(expectedResult).toContain(anEntity2);
      });

      it('should accept null and undefined values', () => {
        const anEntity: IAnEntity = { id: 123 };
        expectedResult = service.addAnEntityToCollectionIfMissing([], null, anEntity, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anEntity);
      });

      it('should return initial array if no AnEntity is added', () => {
        const anEntityCollection: IAnEntity[] = [{ id: 123 }];
        expectedResult = service.addAnEntityToCollectionIfMissing(anEntityCollection, undefined, null);
        expect(expectedResult).toEqual(anEntityCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
