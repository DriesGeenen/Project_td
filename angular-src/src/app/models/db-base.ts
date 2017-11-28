export default class DbBase{
	private id:string;
	public getId():string{
		return this.id;
	}

	constructor(data:any = {}){
		this.id = data._id;
	}

}