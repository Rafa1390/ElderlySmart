import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IUserApp, UserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from './user-app.service';

@Component({
  selector: 'jhi-user-app-update',
  templateUrl: './user-app-update.component.html'
})
export class UserAppUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    identification: [],
    idTypeUser: [],
    email: [],
    name: [],
    name2: [],
    lastName: [],
    lastName2: [],
    phone1: [],
    phone2: [],
    age: [],
    password: [],
    state: []
  });

  constructor(protected userAppService: UserAppService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userApp }) => {
      this.updateForm(userApp);
    });
  }

  updateForm(userApp: IUserApp) {
    this.editForm.patchValue({
      id: userApp.id,
      identification: userApp.identification,
      idTypeUser: userApp.idTypeUser,
      email: userApp.email,
      name: userApp.name,
      name2: userApp.name2,
      lastName: userApp.lastName,
      lastName2: userApp.lastName2,
      phone1: userApp.phone1,
      phone2: userApp.phone2,
      age: userApp.age,
      password: userApp.password,
      state: userApp.state
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userApp = this.createFromForm();
    if (userApp.id !== undefined) {
      this.subscribeToSaveResponse(this.userAppService.update(userApp));
    } else {
      this.subscribeToSaveResponse(this.userAppService.create(userApp));
    }
  }

  private createFromForm(): IUserApp {
    return {
      ...new UserApp(),
      id: this.editForm.get(['id']).value,
      identification: this.editForm.get(['identification']).value,
      idTypeUser: this.editForm.get(['idTypeUser']).value,
      email: this.editForm.get(['email']).value,
      name: this.editForm.get(['name']).value,
      name2: this.editForm.get(['name2']).value,
      lastName: this.editForm.get(['lastName']).value,
      lastName2: this.editForm.get(['lastName2']).value,
      phone1: this.editForm.get(['phone1']).value,
      phone2: this.editForm.get(['phone2']).value,
      age: this.editForm.get(['age']).value,
      password: this.editForm.get(['password']).value,
      state: this.editForm.get(['state']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserApp>>) {
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
