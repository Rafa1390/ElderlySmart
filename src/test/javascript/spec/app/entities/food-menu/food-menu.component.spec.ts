import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { FoodMenuComponent } from 'app/entities/food-menu/food-menu.component';
import { FoodMenuService } from 'app/entities/food-menu/food-menu.service';
import { FoodMenu } from 'app/shared/model/food-menu.model';

describe('Component Tests', () => {
  describe('FoodMenu Management Component', () => {
    let comp: FoodMenuComponent;
    let fixture: ComponentFixture<FoodMenuComponent>;
    let service: FoodMenuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FoodMenuComponent],
        providers: []
      })
        .overrideTemplate(FoodMenuComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FoodMenuComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FoodMenuService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FoodMenu(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.foodMenus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
