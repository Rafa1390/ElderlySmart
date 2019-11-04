import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { FoodMenuDetailComponent } from 'app/entities/food-menu/food-menu-detail.component';
import { FoodMenu } from 'app/shared/model/food-menu.model';

describe('Component Tests', () => {
  describe('FoodMenu Management Detail Component', () => {
    let comp: FoodMenuDetailComponent;
    let fixture: ComponentFixture<FoodMenuDetailComponent>;
    const route = ({ data: of({ foodMenu: new FoodMenu(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FoodMenuDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FoodMenuDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FoodMenuDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.foodMenu).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
