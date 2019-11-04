import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAllergies, Allergies } from 'app/shared/model/allergies.model';
import { AllergiesService } from './allergies.service';
import { ICaseFile } from 'app/shared/model/case-file.model';
import { CaseFileService } from 'app/entities/case-file/case-file.service';

@Component({
  selector: 'jhi-allergies-update',
  templateUrl: './allergies-update.component.html'
})
export class AllergiesUpdateComponent implements OnInit {
  isSaving: boolean;

  casefiles: ICaseFile[];

  editForm = this.fb.group({
    id: [],
    idAllergies: [],
    name: [],
    description: [],
    caseFile: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected allergiesService: AllergiesService,
    protected caseFileService: CaseFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ allergies }) => {
      this.updateForm(allergies);
    });
    this.caseFileService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICaseFile[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICaseFile[]>) => response.body)
      )
      .subscribe((res: ICaseFile[]) => (this.casefiles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(allergies: IAllergies) {
    this.editForm.patchValue({
      id: allergies.id,
      idAllergies: allergies.idAllergies,
      name: allergies.name,
      description: allergies.description,
      caseFile: allergies.caseFile
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const allergies = this.createFromForm();
    if (allergies.id !== undefined) {
      this.subscribeToSaveResponse(this.allergiesService.update(allergies));
    } else {
      this.subscribeToSaveResponse(this.allergiesService.create(allergies));
    }
  }

  private createFromForm(): IAllergies {
    return {
      ...new Allergies(),
      id: this.editForm.get(['id']).value,
      idAllergies: this.editForm.get(['idAllergies']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      caseFile: this.editForm.get(['caseFile']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAllergies>>) {
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

  trackCaseFileById(index: number, item: ICaseFile) {
    return item.id;
  }
}
