import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEntityTest, EntityTest } from '../entity-test.model';

import { EntityTestService } from './entity-test.service';

describe('Service Tests', () => {
  describe('EntityTest Service', () => {
    let service: EntityTestService;
    let httpMock: HttpTestingController;
    let elemDefault: IEntityTest;
    let expectedResult: IEntityTest | IEntityTest[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EntityTestService);
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
        aImageBlobContentType: 'image/png',
        aImageBlob: 'AAAAAAA',
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

      it('should create a EntityTest', () => {
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

        service.create(new EntityTest()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EntityTest', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            aString: 'BBBBBB',
            aInteger: 1,
            aLong: 1,
            aBigDecimal: 1,
            aFloat: 1,
            aDouble: 1,
            aBoolean: true,
            aLocalDate: currentDate.format(DATE_FORMAT),
            aZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
            aInstant: currentDate.format(DATE_TIME_FORMAT),
            aDuration: 'BBBBBB',
            aUUID: 'BBBBBB',
            aBlob: 'BBBBBB',
            aAnyBlob: 'BBBBBB',
            aImageBlob: 'BBBBBB',
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

      it('should partial update a EntityTest', () => {
        const patchObject = Object.assign(
          {
            aString: 'BBBBBB',
            aInteger: 1,
            aLong: 1,
            aBigDecimal: 1,
            aFloat: 1,
            aBoolean: true,
            aZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
            aBlob: 'BBBBBB',
            aTextBlob: 'BBBBBB',
          },
          new EntityTest()
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

      it('should return a list of EntityTest', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            aString: 'BBBBBB',
            aInteger: 1,
            aLong: 1,
            aBigDecimal: 1,
            aFloat: 1,
            aDouble: 1,
            aBoolean: true,
            aLocalDate: currentDate.format(DATE_FORMAT),
            aZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
            aInstant: currentDate.format(DATE_TIME_FORMAT),
            aDuration: 'BBBBBB',
            aUUID: 'BBBBBB',
            aBlob: 'BBBBBB',
            aAnyBlob: 'BBBBBB',
            aImageBlob: 'BBBBBB',
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

      it('should delete a EntityTest', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEntityTestToCollectionIfMissing', () => {
        it('should add a EntityTest to an empty array', () => {
          const entityTest: IEntityTest = { id: 123 };
          expectedResult = service.addEntityTestToCollectionIfMissing([], entityTest);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(entityTest);
        });

        it('should not add a EntityTest to an array that contains it', () => {
          const entityTest: IEntityTest = { id: 123 };
          const entityTestCollection: IEntityTest[] = [
            {
              ...entityTest,
            },
            { id: 456 },
          ];
          expectedResult = service.addEntityTestToCollectionIfMissing(entityTestCollection, entityTest);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EntityTest to an array that doesn't contain it", () => {
          const entityTest: IEntityTest = { id: 123 };
          const entityTestCollection: IEntityTest[] = [{ id: 456 }];
          expectedResult = service.addEntityTestToCollectionIfMissing(entityTestCollection, entityTest);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(entityTest);
        });

        it('should add only unique EntityTest to an array', () => {
          const entityTestArray: IEntityTest[] = [{ id: 123 }, { id: 456 }, { id: 95626 }];
          const entityTestCollection: IEntityTest[] = [{ id: 123 }];
          expectedResult = service.addEntityTestToCollectionIfMissing(entityTestCollection, ...entityTestArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const entityTest: IEntityTest = { id: 123 };
          const entityTest2: IEntityTest = { id: 456 };
          expectedResult = service.addEntityTestToCollectionIfMissing([], entityTest, entityTest2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(entityTest);
          expect(expectedResult).toContain(entityTest2);
        });

        it('should accept null and undefined values', () => {
          const entityTest: IEntityTest = { id: 123 };
          expectedResult = service.addEntityTestToCollectionIfMissing([], null, entityTest, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(entityTest);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
