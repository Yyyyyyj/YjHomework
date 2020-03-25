import React, { Component } from 'react';
import './TodoList.css';

class ListItem extends Component{

    constructor(props){
        super(props)
        this.state = {
            textStyle:"item",
            buttonStyle:"btn",
            buttonStr:"完成"
        }
    }

 
    onFinishBtnClick = () => {
     this.setState({
        textStyle: "done-item",
        buttonStyle:"btn disabled",
        buttonStr:"已完成"
     })
 }
 
    render(){

    const item = this.props.item

     return (
        <tr>
            <th>
                <p class={this.state.textStyle}>{item.content}</p>
            </th>
            <th>
                <button className={this.state.buttonStyle} onClick ={this.onFinishBtnClick} >{this.state.buttonStr}</button>
            </th>
        </tr>
     );
 }
 }

 export default ListItem;