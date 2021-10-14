import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AnEntityService } from '../service/an-entity.service';

import { AnEntityComponent } from './an-entity.component';

describe('Component Tests', () => {
  describe('AnEntity Management Component', () => {
    let comp: AnEntityComponent;
    let fixture: ComponentFixture<AnEntityComponent>;
    let service: AnEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AnEntityComponent],
      })
        .overrideTemplate(AnEntityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnEntityComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(AnEntityService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
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
      expect(comp.anEntities?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
