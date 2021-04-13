import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { EntityTestService } from '../service/entity-test.service';

import { EntityTestComponent } from './entity-test.component';

describe('Component Tests', () => {
  describe('EntityTest Management Component', () => {
    let comp: EntityTestComponent;
    let fixture: ComponentFixture<EntityTestComponent>;
    let service: EntityTestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EntityTestComponent],
      })
        .overrideTemplate(EntityTestComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityTestComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(EntityTestService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.entityTests?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
