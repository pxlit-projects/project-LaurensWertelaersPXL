import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-filter',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css'
})
export class FilterComponent {
  content: string = '';
  usernameWriter: string = '';
  creationDate: string = ''; // Dit kan een string zijn in het formaat 'YYYY-MM-DD'

  @Output() filterChanged = new EventEmitter<any>();

  applyFilter() {
    this.filterChanged.emit({
      content: this.content,
      usernameWriter: this.usernameWriter,
      creationDate: this.creationDate
    });
  }

  clearFilter() {
    this.content = '';
    this.usernameWriter = '';
    this.creationDate = '';
    this.applyFilter();  // Emit an empty filter
  }
}
