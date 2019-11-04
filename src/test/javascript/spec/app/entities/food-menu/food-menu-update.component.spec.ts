import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { FoodMenuUpdateComponent } from 'app/entities/food-menu/food-menu-update.component';
import { FoodMenuService } from 'app/entities/food-menu/food-menu.service';
import { FoodMenu } from 'app/shared/model/food-menu.model';

describe('Component Tests', () => {
  describe('FoodMenu Management Update Component', () => {
    let comp: FoodMenuUpdateComponent;
    let fixture: ComponentFixture<FoodMenuUpdateComponent>;
    let service: FoodMenuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FoodMenuUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FoodMenuUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FoodMenuUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FoodMenuService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FoodMenu(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FoodMenu();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
