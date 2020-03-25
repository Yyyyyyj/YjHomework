import React, { Component } from 'react';

class TodoListHeader extends Component{

    render(){
        const todoListHeader = 'Todo List';
        return (
              <h2>{todoListHeader}</h2>
        );
    }
}

export default TodoListHeader;