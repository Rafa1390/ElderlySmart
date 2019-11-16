import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFamily, Family } from 'app/shared/model/family.model';
import { FamilyService } from './family.service';
import { IElderly } from 'app/shared/model/elderly.model';
import { ElderlyService } from 'app/entities/elderly/elderly.service';

@Component({
  selector: 'jhi-family-update',
  templateUrl: './family-update.component.html'
})
export class FamilyUpdateComponent implements OnInit {
  isSaving: boolean;

  elderlies: IElderly[];

  editForm = this.fb.group({
    id: [],
    idFamily: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    name2: [],
    lastName: new FormControl('', [Validators.required]),
    lastName2: [],
    phone1: new FormControl('', [Validators.required]),
    phone2: [],
    age: new FormControl('', [Validators.min(18), Validators.max(90)]),
    familyRelation: new FormControl('', [Validators.required]),
    state: new FormControl('', [Validators.required]),
    elderlies: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected familyService: FamilyService,
    protected elderlyService: ElderlyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  get idFamily() {
    return this.editForm.get('idFamily');
  }
  get name() {
    return this.editForm.get('name');
  }
  get name2() {
    return this.editForm.get('name2');
  }
  get lastName() {
    return this.editForm.get('lastName');
  }
  get lastName2() {
    return this.editForm.get('lastName2');
  }
  get age() {
    return this.editForm.get('age');
  }
  get familyRelation() {
    return this.editForm.get('familyRelation');
  }
  get state() {
    return this.editForm.get('state');
  }
  get phone1() {
    return this.editForm.get('phone1');
  }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ family }) => {
      this.updateForm(family);
    });
    this.elderlyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IElderly[]>) => mayBeOk.ok),
        map((response: HttpResponse<IElderly[]>) => response.body)
      )
      .subscribe((res: IElderly[]) => (this.elderlies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(family: IFamily) {
    this.editForm.patchValue({
      id: family.id,
      idFamily: family.idFamily,
      name: family.name,
      name2: family.name2,
      lastName: family.lastName,
      lastName2: family.lastName2,
      phone1: family.phone1,
      phone2: family.phone2,
      age: family.age,
      familyRelation: family.familyRelation,
      state: family.state,
      elderlies: family.elderlies
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const family = this.createFromForm();
    if (family.id !== undefined) {
      this.subscribeToSaveResponse(this.familyService.update(family));
    } else {
      this.subscribeToSaveResponse(this.familyService.create(family));
    }
  }

  private createFromForm(): IFamily {
    return {
      ...new Family(),
      id: this.editForm.get(['id']).value,
      idFamily: this.editForm.get(['idFamily']).value,
      name: this.editForm.get(['name']).value,
      name2: this.editForm.get(['name2']).value,
      lastName: this.editForm.get(['lastName']).value,
      lastName2: this.editForm.get(['lastName2']).value,
      phone1: this.editForm.get(['phone1']).value,
      phone2: this.editForm.get(['phone2']).value,
      age: this.editForm.get(['age']).value,
      familyRelation: this.editForm.get(['familyRelation']).value,
      state: this.editForm.get(['state']).value,
      elderlies: this.editForm.get(['elderlies']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamily>>) {
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

  trackElderlyById(index: number, item: IElderly) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
