import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRecreationalActivity, RecreationalActivity } from 'app/shared/model/recreational-activity.model';
import { RecreationalActivityService } from './recreational-activity.service';
import { IAsylum } from 'app/shared/model/asylum.model';
import { AsylumService } from 'app/entities/asylum/asylum.service';

@Component({
  selector: 'jhi-recreational-activity-update',
  templateUrl: './recreational-activity-update.component.html'
})
export class RecreationalActivityUpdateComponent implements OnInit {
  isSaving: boolean;

  asylums: IAsylum[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    idRecreationalActivity: [],
    name: [],
    description: [],
    date: [],
    startTime: [],
    endTime: [],
    state: [],
    asylum: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected recreationalActivityService: RecreationalActivityService,
    protected asylumService: AsylumService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ recreationalActivity }) => {
      this.updateForm(recreationalActivity);
    });
    this.asylumService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAsylum[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAsylum[]>) => response.body)
      )
      .subscribe((res: IAsylum[]) => (this.asylums = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(recreationalActivity: IRecreationalActivity) {
    this.editForm.patchValue({
      id: recreationalActivity.id,
      idRecreationalActivity: recreationalActivity.idRecreationalActivity,
      name: recreationalActivity.name,
      description: recreationalActivity.description,
      date: recreationalActivity.date,
      startTime: recreationalActivity.startTime,
      endTime: recreationalActivity.endTime,
      state: recreationalActivity.state,
      asylum: recreationalActivity.asylum
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const recreationalActivity = this.createFromForm();
    if (recreationalActivity.id !== undefined) {
      this.subscribeToSaveResponse(this.recreationalActivityService.update(recreationalActivity));
    } else {
      this.subscribeToSaveResponse(this.recreationalActivityService.create(recreationalActivity));
    }
  }

  private createFromForm(): IRecreationalActivity {
    return {
      ...new RecreationalActivity(),
      id: this.editForm.get(['id']).value,
      idRecreationalActivity: this.editForm.get(['idRecreationalActivity']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      date: this.editForm.get(['date']).value,
      startTime: this.editForm.get(['startTime']).value,
      endTime: this.editForm.get(['endTime']).value,
      state: this.editForm.get(['state']).value,
      asylum: this.editForm.get(['asylum']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecreationalActivity>>) {
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
