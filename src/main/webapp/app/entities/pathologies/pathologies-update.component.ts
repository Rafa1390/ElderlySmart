import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPathologies, Pathologies } from 'app/shared/model/pathologies.model';
import { PathologiesService } from './pathologies.service';
import { ICaseFile } from 'app/shared/model/case-file.model';
import { CaseFileService } from 'app/entities/case-file/case-file.service';

@Component({
  selector: 'jhi-pathologies-update',
  templateUrl: './pathologies-update.component.html'
})
export class PathologiesUpdateComponent implements OnInit {
  isSaving: boolean;

  casefiles: ICaseFile[];

  editForm = this.fb.group({
    id: [],
    idPathologies: [],
    name: [],
    description: [],
    caseFile: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pathologiesService: PathologiesService,
    protected caseFileService: CaseFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pathologies }) => {
      this.updateForm(pathologies);
    });
    this.caseFileService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICaseFile[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICaseFile[]>) => response.body)
      )
      .subscribe((res: ICaseFile[]) => (this.casefiles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pathologies: IPathologies) {
    this.editForm.patchValue({
      id: pathologies.id,
      idPathologies: pathologies.idPathologies,
      name: pathologies.name,
      description: pathologies.description,
      caseFile: pathologies.caseFile
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pathologies = this.createFromForm();
    if (pathologies.id !== undefined) {
      this.subscribeToSaveResponse(this.pathologiesService.update(pathologies));
    } else {
      this.subscribeToSaveResponse(this.pathologiesService.create(pathologies));
    }
  }

  private createFromForm(): IPathologies {
    return {
      ...new Pathologies(),
      id: this.editForm.get(['id']).value,
      idPathologies: this.editForm.get(['idPathologies']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      caseFile: this.editForm.get(['caseFile']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPathologies>>) {
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
