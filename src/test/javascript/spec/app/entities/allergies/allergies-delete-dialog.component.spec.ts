import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { AllergiesDeleteDialogComponent } from 'app/entities/allergies/allergies-delete-dialog.component';
import { AllergiesService } from 'app/entities/allergies/allergies.service';

describe('Component Tests', () => {
  describe('Allergies Management Delete Component', () => {
    let comp: AllergiesDeleteDialogComponent;
    let fixture: ComponentFixture<AllergiesDeleteDialogComponent>;
    let service: AllergiesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [AllergiesDeleteDialogComponent]
      })
        .overrideTemplate(AllergiesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllergiesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllergiesService);
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
