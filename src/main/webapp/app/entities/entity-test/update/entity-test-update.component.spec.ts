jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EntityTestService } from '../service/entity-test.service';
import { IEntityTest, EntityTest } from '../entity-test.model';

import { EntityTestUpdateComponent } from './entity-test-update.component';

describe('Component Tests', () => {
  describe('EntityTest Management Update Component', () => {
    let comp: EntityTestUpdateComponent;
    let fixture: ComponentFixture<EntityTestUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let entityTestService: EntityTestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EntityTestUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EntityTestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntityTestUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      entityTestService = TestBed.inject(EntityTestService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const entityTest: IEntityTest = { id: 456 };

        activatedRoute.data = of({ entityTest });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(entityTest));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const entityTest = { id: 123 };
        spyOn(entityTestService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ entityTest });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: entityTest }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(entityTestService.update).toHaveBeenCalledWith(entityTest);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const entityTest = new EntityTest();
        spyOn(entityTestService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ entityTest });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: entityTest }));
        saveSubject.complete();

        // THEN
        expect(entityTestService.create).toHaveBeenCalledWith(entityTest);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const entityTest = { id: 123 };
        spyOn(entityTestService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ entityTest });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(entityTestService.update).toHaveBeenCalledWith(entityTest);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
