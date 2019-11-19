import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFuneralPackages, FuneralPackages } from 'app/shared/model/funeral-packages.model';
import { FuneralPackagesService } from './funeral-packages.service';
import { IMortuary } from 'app/shared/model/mortuary.model';
import { MortuaryService } from 'app/entities/mortuary/mortuary.service';

@Component({
  selector: 'jhi-funeral-packages-update',
  templateUrl: './funeral-packages-update.component.html'
})
export class FuneralPackagesUpdateComponent implements OnInit {
  isSaving: boolean;

  mortuaries: IMortuary[];

  editForm = this.fb.group({
    id: [],
    idFuneralPackages: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.required]),
    state: new FormControl('', [Validators.required]),
    mortuary: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected funeralPackagesService: FuneralPackagesService,
    protected mortuaryService: MortuaryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ funeralPackages }) => {
      this.updateForm(funeralPackages);
    });
    this.mortuaryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMortuary[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMortuary[]>) => response.body)
      )
      .subscribe((res: IMortuary[]) => (this.mortuaries = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(funeralPackages: IFuneralPackages) {
    this.editForm.patchValue({
      id: funeralPackages.id,
      idFuneralPackages: funeralPackages.idFuneralPackages,
      name: funeralPackages.name,
      description: funeralPackages.description,
      price: funeralPackages.price,
      state: funeralPackages.state,
      mortuary: funeralPackages.mortuary
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const funeralPackages = this.createFromForm();
    if (funeralPackages.id !== undefined) {
      this.subscribeToSaveResponse(this.funeralPackagesService.update(funeralPackages));
    } else {
      this.subscribeToSaveResponse(this.funeralPackagesService.create(funeralPackages));
    }
  }

  private createFromForm(): IFuneralPackages {
    return {
      ...new FuneralPackages(),
      id: this.editForm.get(['id']).value,
      idFuneralPackages: this.editForm.get(['idFuneralPackages']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      price: this.editForm.get(['price']).value,
      state: this.editForm.get(['state']).value,
      mortuary: this.editForm.get(['mortuary']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuneralPackages>>) {
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

  trackMortuaryById(index: number, item: IMortuary) {
    return item.id;
  }

  get idFuneralPackages() {
    return this.editForm.get('idFuneralPackages');
  }

  get name() {
    return this.editForm.get('name');
  }

  get description() {
    return this.editForm.get('description');
  }

  get price() {
    return this.editForm.get('price');
  }

  get state() {
    return this.editForm.get('state');
  }
}
