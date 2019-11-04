import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMedicationSchedule, MedicationSchedule } from 'app/shared/model/medication-schedule.model';
import { MedicationScheduleService } from './medication-schedule.service';

@Component({
  selector: 'jhi-medication-schedule-update',
  templateUrl: './medication-schedule-update.component.html'
})
export class MedicationScheduleUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    idMedicationSchedule: [],
    meticationName: [],
    elderly: [],
    dose: [],
    time: [],
    state: []
  });

  constructor(
    protected medicationScheduleService: MedicationScheduleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ medicationSchedule }) => {
      this.updateForm(medicationSchedule);
    });
  }

  updateForm(medicationSchedule: IMedicationSchedule) {
    this.editForm.patchValue({
      id: medicationSchedule.id,
      idMedicationSchedule: medicationSchedule.idMedicationSchedule,
      meticationName: medicationSchedule.meticationName,
      elderly: medicationSchedule.elderly,
      dose: medicationSchedule.dose,
      time: medicationSchedule.time,
      state: medicationSchedule.state
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const medicationSchedule = this.createFromForm();
    if (medicationSchedule.id !== undefined) {
      this.subscribeToSaveResponse(this.medicationScheduleService.update(medicationSchedule));
    } else {
      this.subscribeToSaveResponse(this.medicationScheduleService.create(medicationSchedule));
    }
  }

  private createFromForm(): IMedicationSchedule {
    return {
      ...new MedicationSchedule(),
      id: this.editForm.get(['id']).value,
      idMedicationSchedule: this.editForm.get(['idMedicationSchedule']).value,
      meticationName: this.editForm.get(['meticationName']).value,
      elderly: this.editForm.get(['elderly']).value,
      dose: this.editForm.get(['dose']).value,
      time: this.editForm.get(['time']).value,
      state: this.editForm.get(['state']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicationSchedule>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
