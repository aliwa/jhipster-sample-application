jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AnEntityService } from '../service/an-entity.service';
import { IAnEntity, AnEntity } from '../an-entity.model';

import { AnEntityUpdateComponent } from './an-entity-update.component';

describe('Component Tests', () => {
  describe('AnEntity Management Update Component', () => {
    let comp: AnEntityUpdateComponent;
    let fixture: ComponentFixture<AnEntityUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let anEntityService: AnEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AnEntityUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AnEntityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnEntityUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      anEntityService = TestBed.inject(AnEntityService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const anEntity: IAnEntity = { id: 456 };

        activatedRoute.data = of({ anEntity });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(anEntity));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnEntity>>();
        const anEntity = { id: 123 };
        jest.spyOn(anEntityService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anEntity });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: anEntity }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(anEntityService.update).toHaveBeenCalledWith(anEntity);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnEntity>>();
        const anEntity = new AnEntity();
        jest.spyOn(anEntityService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anEntity });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: anEntity }));
        saveSubject.complete();

        // THEN
        expect(anEntityService.create).toHaveBeenCalledWith(anEntity);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnEntity>>();
        const anEntity = { id: 123 };
        jest.spyOn(anEntityService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anEntity });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(anEntityService.update).toHaveBeenCalledWith(anEntity);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
