import { Component, OnInit } from '@angular/core';
import autoBind from 'auto-bind';
import {HallService} from "../../services/hall.service";
import { NgForm } from '@angular/forms';
import Hall from '../../models/hall';
import { ActivatedRoute, Router } from '@angular/router';
import handleHttpError from '../../extensions/errorHandle';

@Component({
  selector: 'app-add-hall',
  templateUrl: './add-hall.component.html',
  styleUrls: ['./add-hall.component.css']
})
export class AddHallComponent implements OnInit {

  hall:Hall = new Hall({hallType:"", description:""});
  sub:any;

  constructor(private hallService:HallService, private route:ActivatedRoute, private router:Router) {
    autoBind(this);
  }

  async ngOnInit() {
    this.sub = this.route.params.subscribe(function(params){this.getHall(params["hallId"]);}.bind(this))
  }

  async getHall(hallId:string){
    if (hallId == null || hallId == ""){
      this.hall = new Hall({hallType:"", description:""});
    }else{
      try{
        this.hall = await this.hallService.getHall(hallId);
      }catch(err){
        console.log(err);
      }
    }
  }

  ngOnDestroy(){
    this.sub.unsubscribe();
  }

  async onSubmit(){
    try{
      console.log(this.hall);
      if (this.hall.getId()){
        await this.hallService.updateHall(this.hall);
      }
      else{
        await this.hallService.createHall(this.hall);
      }
      this.router.navigate(["/"]);
    }catch(err){
      handleHttpError(err);
    }
  }
}
