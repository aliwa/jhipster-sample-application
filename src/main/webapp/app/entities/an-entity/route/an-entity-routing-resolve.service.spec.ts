jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAnEntity, AnEntity } from '../an-entity.model';
import { AnEntityService } from '../service/an-entity.service';

import { AnEntityRoutingResolveService } from './an-entity-routing-resolve.service';

describe('AnEntity routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AnEntityRoutingResolveService;
  let service: AnEntityService;
  let resultAnEntity: IAnEntity | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(AnEntityRoutingResolveService);
    service = TestBed.inject(AnEntityService);
    resultAnEntity = undefined;
  });

  describe('resolve', () => {
    it('should return IAnEntity returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAnEntity = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAnEntity).toEqual({ id: 123 });
    });

    it('should return new IAnEntity if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAnEntity = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAnEntity).toEqual(new AnEntity());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AnEntity })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAnEntity = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAnEntity).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
