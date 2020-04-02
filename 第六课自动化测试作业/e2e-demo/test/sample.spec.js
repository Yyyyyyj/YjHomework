describe('add todo', function () {
    let page;

    before (async function () {
      page = await browser.newPage();
      await page.goto('http://127.0.0.1:7001/');
    });
  
    after (async function () {
      await page.close();
    });

    it('should have correct title', async function() {
        expect(await page.title()).to.eql('Puppeteer Demo');
    })

    //添加新的代办事项
    it('should new todo correct', async function() {
      await page.click('#new-todo', {delay: 500});
      await page.type('#new-todo', 'new todo item', {delay: 50});
      await page.keyboard.press("Enter");
      await page.click('#new-todo', {delay: 500});
      await page.type('#new-todo', 'new todo item1', {delay: 50});
      await page.keyboard.press("Enter");
      await page.click('#new-todo', {delay: 500});
      await page.type('#new-todo', 'new todo item2', {delay: 50});
      await page.keyboard.press("Enter");
      let todoList = await page.waitFor('#todo-list');
      const expectInputContent = await page.evaluate(todoList => todoList.lastChild.querySelector('label').textContent, todoList);
      expect(expectInputContent).to.eql('new todo item2');
    }) 
    
    //完成事项
    it('should completed todo correct', async function() {
      //读取列表任务原有状态
      const status = await page.$$eval('#todo-list > li', eles => {
        var arr = new Array(eles.length);
        for(var i = 0; i<eles.length; i++){
          arr[i] = eles[i].getAttribute( 'class' );
        }
        return arr;
      });
      //点击列表中的某个任务
      const index = await page.$$eval('input[type=checkbox][class=toggle]', lis => {
        const i = Math.floor(Math.random()*lis.length);
        lis[i].click();
        return i;
      });
      await page.waitFor(500);
      //读取更新后的列表状态
      const updateStatus = await page.$$eval('#todo-list > li', updateeles => {
        var updatearr = new Array(updateeles.length);
        for(var i = 0; i<updateeles.length; i++){
          updatearr[i] = updateeles[i].getAttribute( 'class' );
        }
        return updatearr;
      });
      //判断状态是否改变
      if(status[index] === 'completed'){
        expect(updateStatus[index]).to.eql('');
      }else{
        expect(updateStatus[index]).to.eql('completed');
      }
    })


    //判断未完成事项的长度
    it('should count unfinshedTodo correct', async function() {
      const expectLeft= await page.$$eval('#todo-list > li', lis => {
        var countLeft = 0;
        for( var i = 0; i<lis.length; i++){
          const text = lis[i].getAttribute( 'class' );
          if(text != 'completed'){
            countLeft++;
          }
        }
        return countLeft;
      });
      const realLeft = await page.$eval('#todo-count > strong', ele => ele.innerText);
      expect(expectLeft).to.eql(+realLeft);
    })
  });