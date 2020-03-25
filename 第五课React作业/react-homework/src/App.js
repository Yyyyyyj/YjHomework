import React, {Component} from 'react';
import './TodoList.css';
import TodoListHeader from './TodoListHeader';
import TodoList from './TodoList';

class App extends Component{
  render() {
    return (
        <div className="body">
          <TodoListHeader />
          <TodoList />
        </div>
    );
  }
}

export default App;
      

