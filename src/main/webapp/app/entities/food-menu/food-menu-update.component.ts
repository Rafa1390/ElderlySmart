import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFoodMenu, FoodMenu } from 'app/shared/model/food-menu.model';
import { FoodMenuService } from './food-menu.service';
import { IAsylum } from 'app/shared/model/asylum.model';
import { AsylumService } from 'app/entities/asylum/asylum.service';

@Component({
  selector: 'jhi-food-menu-update',
  templateUrl: './food-menu-update.component.html'
})
export class FoodMenuUpdateComponent implements OnInit {
  isSaving: boolean;

  asylums: IAsylum[];

  editForm = this.fb.group({
    id: [],
    idFoodMenu: [],
    feedingTime: [],
    description: [],
    asylum: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected foodMenuService: FoodMenuService,
    protected asylumService: AsylumService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ foodMenu }) => {
      this.updateForm(foodMenu);
    });
    this.asylumService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAsylum[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAsylum[]>) => response.body)
      )
      .subscribe((res: IAsylum[]) => (this.asylums = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(foodMenu: IFoodMenu) {
    this.editForm.patchValue({
      id: foodMenu.id,
      idFoodMenu: foodMenu.idFoodMenu,
      feedingTime: foodMenu.feedingTime,
      description: foodMenu.description,
      asylum: foodMenu.asylum
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const foodMenu = this.createFromForm();
    if (foodMenu.id !== undefined) {
      this.subscribeToSaveResponse(this.foodMenuService.update(foodMenu));
    } else {
      this.subscribeToSaveResponse(this.foodMenuService.create(foodMenu));
    }
  }

  private createFromForm(): IFoodMenu {
    return {
      ...new FoodMenu(),
      id: this.editForm.get(['id']).value,
      idFoodMenu: this.editForm.get(['idFoodMenu']).value,
      feedingTime: this.editForm.get(['feedingTime']).value,
      description: this.editForm.get(['description']).value,
      asylum: this.editForm.get(['asylum']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoodMenu>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAsylumById(index: number, item: IAsylum) {
    return item.id;
  }
}
