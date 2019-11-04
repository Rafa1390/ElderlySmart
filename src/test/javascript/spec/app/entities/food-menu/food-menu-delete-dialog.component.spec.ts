import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { FoodMenuDeleteDialogComponent } from 'app/entities/food-menu/food-menu-delete-dialog.component';
import { FoodMenuService } from 'app/entities/food-menu/food-menu.service';

describe('Component Tests', () => {
  describe('FoodMenu Management Delete Component', () => {
    let comp: FoodMenuDeleteDialogComponent;
    let fixture: ComponentFixture<FoodMenuDeleteDialogComponent>;
    let service: FoodMenuService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FoodMenuDeleteDialogComponent]
      })
        .overrideTemplate(FoodMenuDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FoodMenuDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FoodMenuService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
