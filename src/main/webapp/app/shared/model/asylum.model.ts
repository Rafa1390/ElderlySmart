import { IUserApp } from 'app/shared/model/user-app.model';
import { ICleaningProgram } from 'app/shared/model/cleaning-program.model';
import { IFoodMenu } from 'app/shared/model/food-menu.model';
import { IRecreationalActivity } from 'app/shared/model/recreational-activity.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IMortuary } from 'app/shared/model/mortuary.model';
import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { IPartner } from 'app/shared/model/partner.model';

export interface IAsylum {
  id?: number;
  identification?: string;
  email?: string;
  name?: string;
  address?: string;
  userApp?: IUserApp;
  cleaningPrograms?: ICleaningProgram[];
  foodMenus?: IFoodMenu[];
  recreationalActivities?: IRecreationalActivity[];
  employees?: IEmployee[];
  mortuaries?: IMortuary[];
  pharmacies?: IPharmacy[];
  partners?: IPartner[];
}

export class Asylum implements IAsylum {
  constructor(
    public id?: number,
    public identification?: string,
    public email?: string,
    public name?: string,
    public address?: string,
    public userApp?: IUserApp,
    public cleaningPrograms?: ICleaningProgram[],
    public foodMenus?: IFoodMenu[],
    public recreationalActivities?: IRecreationalActivity[],
    public employees?: IEmployee[],
    public mortuaries?: IMortuary[],
    public pharmacies?: IPharmacy[],
    public partners?: IPartner[]
  ) {}
}
