# Git的使用 #

## 配置用户名和邮箱地址 ##
每次提交git都会记录提交者的name和email，所以最好先配置下你的name和email  

	$ git config --global user.name 'you_name'  
	$ git config --global user.email you_email

## 生成SSH key ##
[如何生成git的公钥和私钥](http://blog.csdn.net/wqjsir/article/details/17386087)  
[Git SSH Key生成步骤](http://www.asheep.cn/skill/git-ssh-key.html)

## 从远程库克隆 ##
使用ssh需要先生成key并在github上配置好  
可以在用户主目录里找到.ssh目录，里面有id_rsa和id_rsa.pub两个文件，这两个就是SSH Key的秘钥对，id_rsa是私钥，不能泄露出去，id_rsa.pub是公钥，可以放心地告诉任何人。  
[git-ssh 配置和使用](https://segmentfault.com/a/1190000002645623)

	git clone http或ssh

### clone某一分支 ###
	
	git clone -b <branch> <remote_repo> 例如： git clone -b 指定的分支名字

## 提交 ##
###git add
将该文件添加到缓存

	git add hello.php

###git status  
查看在你上次提交之后是否有修改  

	git status -s

### git commit ###
使用 git add 命令将想要快照的内容写入缓存区， 而执行 git commit 将缓存区内容添加到仓库中。  

	git commit -m '第一次版本提交'

### 提交到服务器 ###
	git push ssh地址 master    //默认情况下 这条语句等价于提交 本地的master分支，到远程仓库，并作为远程的master分支
	git push ssh地址 test:test //提交本地test分支作为远程的test分支

或者

	git remote add origin ssh://git@dev.lemote.com/rt4ls.git
	git push origin master   

这两个操作是等价的，第二个操作的第一行的意思是添加一个标记，让origin指向这个ssh地址

[git push 小结](http://blog.csdn.net/wh_19910525/article/details/7438183)

### 提交已修改的文件 ###
	git add .
	git add --update .
	git commit -am "add User.java"

## git 回滚到某一版本 ##

[git 回滚到某一版本](http://jingyan.baidu.com/article/76a7e409dc285cfc3b6e1587.html)  
[Git回滚的常用手法](http://hittyt.iteye.com/blog/1961386)  

### working tree内的回滚 ###
- git checkout file1 （回滚单个文件）
- git checkout file1 file2 ... fileN （一次回滚多个文件，中间用空格隔开即可）
- git checkout . （直接回滚当前目录一下的所有working tree内的修改，会递归扫描当前目录下的所有子目录）

### index内的回滚 ###
1. 将index区域中修改过的文件移除index，也就是恢复到working tree中。这部用git reset来解决。 (git reset filename)  
1. 一旦文件重新回到working tree中，回滚操作就是上面提到的git checkout喽。

### 回滚中间的某次提交 ###
1. git show HEAD^0 看看那次提交都干了什么
1. git revert HEAD^0 来回滚这次操作

> 其中 0 代指哪一次，0为具体提交最近的一次  

### 回滚最后的N次提交 ###
通过 git log 查看 提交记录
git reset --soft HEAD~2
再通过 git log 查看 ， 发现最近N次的提交记录不见了
用 git status 查看，发现几个modified文件 (就是reset回来的文件)
用 get checkout 文件名 来完成最后的回滚

> --soft（个人推荐使用这个，他不会修改你目前index或者working tree中所做的任何修改  
> --mixed（你在reset时不加任何参数时的默认行为，会默默把你在index中的修改给灭了！  
> --hard（这个是我绝的最危险的参数，会把你index和working tree中的所有修改毁灭的毛都不剩，使用之前请三思，这确实是你要的行为！）

## 创建分支 ##

-b 表示创建并切换

	git checkout -b 分支名  

相当于

	git branch dev
	git checkout dev  

然后，用git branch命令查看当前分支：  

	git branch

然后,提交到服务器
	git push ssh地址 dev名称 //如果服务器没有该分支名称，则会新建

## 合并分支 ##
[git分支的合并](http://blog.csdn.net/hudashi/article/details/7668798/)  

合并某分支到当前分支：git merge <name>   
如果有冲突，则手动进行文件的合并后，再  

	git add <name>
	git commit


### 删除远程分支 ###
查看  
	
	git branch -a

删除远程分支  
	
	git branch -r -d origin/branch-name
	git push origin :branch-name //注意origin后面的空格

### 常用分支命令 ###

	查看分支：git branch  
	创建分支：git branch <name>  
	切换分支：git checkout <name>  
	创建+切换分支：git checkout -b <name>  
	合并某分支到当前分支：git merge <name>    
	删除分支：git branch -d <name>  
	分支合并  
     比如，如果要将开发中的分支（develop），合并到稳定分支（master），  
     首先切换的master分支：git checkout master。  
     然后执行合并操作：git merge develop。  
     如果有冲突，会提示你，调用git status查看冲突文件。  
     解决冲突，然后调用git add或git rm将解决后的文件暂存。  
     所有冲突解决后，git commit 提交更改。  
	分支衍合  
     分支衍合和分支合并的差别在于，分支衍合不会保留合并的日志，不留痕迹，而 分支合并则会保留合并的日志。  
     要将开发中的分支（develop），衍合到稳定分支（master）。  
     首先切换的master分支：git checkout master。  
     然后执行衍和操作：git rebase develop。  
     如果有冲突，会提示你，调用git status查看冲突文件。  
     解决冲突，然后调用git add或git rm将解决后的文件暂存。  
     所有冲突解决后，git rebase --continue 提交更改。  

### 分支策略 ###
在实际开发中，我们应该按照几个基本原则进行分支管理：  
首先，master分支应该是非常稳定的，也就是仅用来发布新版本，平时不能在上面干活；  
那在哪干活呢？干活都在dev分支上，也就是说，dev分支是不稳定的，到某个时候，比如1.0版本发布时，再把dev分支合并到master上，在master分支发布1.0版本；  
你和你的小伙伴们每个人都在dev分支上干活，每个人都有自己的分支，时不时地往dev分支上合并就可以了。  
所以，团队合作的分支看起来就像这样：  
![](http://www.liaoxuefeng.com/files/attachments/001384909239390d355eb07d9d64305b6322aaf4edac1e3000/0)  

##常见问题
> Git – fatal: Unable to create ‘/.git/index.lock’: File exists.  
> fatal: Unable to create ‘/path/my_proj/.git/index.lock’: File exists.  
> If no other git process is currently running, this probably means a git process crashed in this repository earlier. Make sure no other git process is running and remove the file manually to continue.

####解决方法 
可以试着删除 index.lock
	
	rm -f ./.git/index.lock

## 其他 ##
> 学习自  
> [runoob.com](http://www.runoob.com/git/git-basic-operations.html)    
> [oschina-git](http://git.oschina.net/oschina/git-osc/wikis/%E5%B8%AE%E5%8A%A9#ssh-keys)  
> [廖雪峰的Git教程](http://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000)

