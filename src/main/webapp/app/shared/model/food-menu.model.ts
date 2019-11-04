import { IAsylum } from 'app/shared/model/asylum.model';

export interface IFoodMenu {
  id?: number;
  idFoodMenu?: number;
  feedingTime?: string;
  description?: string;
  asylum?: IAsylum;
}

export class FoodMenu implements IFoodMenu {
  constructor(
    public id?: number,
    public idFoodMenu?: number,
    public feedingTime?: string,
    public description?: string,
    public asylum?: IAsylum
  ) {}
}
