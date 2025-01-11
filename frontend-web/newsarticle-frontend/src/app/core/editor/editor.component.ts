import { Component, OnInit } from '@angular/core';
import { NewsarticleListComponent } from "../newsarticle/newsarticle-list/newsarticle-list.component";

@Component({
  selector: 'app-editor',
  standalone: true,
  imports: [NewsarticleListComponent],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.css'
})
export class EditorComponent  {

}
