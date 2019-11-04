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
import { ICleaningProgram, CleaningProgram } from 'app/shared/model/cleaning-program.model';
import { CleaningProgramService } from './cleaning-program.service';
import { IAsylum } from 'app/shared/model/asylum.model';
import { AsylumService } from 'app/entities/asylum/asylum.service';

@Component({
  selector: 'jhi-cleaning-program-update',
  templateUrl: './cleaning-program-update.component.html'
})
export class CleaningProgramUpdateComponent implements OnInit {
  isSaving: boolean;

  asylums: IAsylum[];
  dayDp: any;

  editForm = this.fb.group({
    id: [],
    idCleaningProgram: [],
    day: [],
    time: [],
    cleaningTask: [],
    asylum: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cleaningProgramService: CleaningProgramService,
    protected asylumService: AsylumService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cleaningProgram }) => {
      this.updateForm(cleaningProgram);
    });
    this.asylumService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAsylum[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAsylum[]>) => response.body)
      )
      .subscribe((res: IAsylum[]) => (this.asylums = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cleaningProgram: ICleaningProgram) {
    this.editForm.patchValue({
      id: cleaningProgram.id,
      idCleaningProgram: cleaningProgram.idCleaningProgram,
      day: cleaningProgram.day,
      time: cleaningProgram.time,
      cleaningTask: cleaningProgram.cleaningTask,
      asylum: cleaningProgram.asylum
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cleaningProgram = this.createFromForm();
    if (cleaningProgram.id !== undefined) {
      this.subscribeToSaveResponse(this.cleaningProgramService.update(cleaningProgram));
    } else {
      this.subscribeToSaveResponse(this.cleaningProgramService.create(cleaningProgram));
    }
  }

  private createFromForm(): ICleaningProgram {
    return {
      ...new CleaningProgram(),
      id: this.editForm.get(['id']).value,
      idCleaningProgram: this.editForm.get(['idCleaningProgram']).value,
      day: this.editForm.get(['day']).value,
      time: this.editForm.get(['time']).value,
      cleaningTask: this.editForm.get(['cleaningTask']).value,
      asylum: this.editForm.get(['asylum']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICleaningProgram>>) {
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
