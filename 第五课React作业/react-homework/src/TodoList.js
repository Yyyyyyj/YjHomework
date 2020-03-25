import React, { Component } from 'react';
import './TodoList.css';
import NewItem from './NewItem';
import ListItem from './ListItem';

class TodoList extends Component{

    constructor(props){
        super(props);
        this.state = {
            todoList:[
                {content: 'React practice'},
                {content: 'game time'}
            ]
        }
    }

    addNewItem = (newItemContent) => {
        const newList = [...this.state.todoList, {content:newItemContent}];
        this.setState({
            todoList: newList
        })
    }

    render(){
        return (
            <div>
              <table className="table"> 
                    {
                        this.state.todoList.map(item => <ListItem item={item}/>)
                    }
                <NewItem addNewItem={this.addNewItem}/>
              </table>
            </div>
        );
    }
}

export default TodoList;