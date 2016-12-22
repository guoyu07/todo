全局配置
------------
#配置使用git仓库的人员姓名 
git config --global user.name "Your Name Comes Here" 

#配置使用git仓库的人员email 
git config --global user.email you@yourdomain.example.com 

#配置到缓存 默认15分钟 
git config --global credential.helper cache

#修改缓存时间 
git config --global credential.helper 'cache --timeout=3600'   

#ui
git config --global color.ui true

git config --global alias.co checkout

#hg 切换分支
git config --global alias.up checkout

#提交
git config --global alias.ci commit

#查看状态
git config --global alias.st status

#新建分支
git config --global alias.br branch

#首先请保证的确有该编辑器，并且将其添加到path里面（如：D:\Program Files\Sublime Text 3）
#如果不想配置，则不需要进行如下配置。
git config --global core.editor "sublime_text"    # 设置Editor使用textmate 

git config -l #列举所有配置


常用操作
-----------
1.git clone 或 git create
2.记得一定先看下当前分支是master还是开发分支。大家切记master主干不要操作。master主干用来拉代码和发版用
3.创建分支：git branch dev（你的分支）
4.每次修改前一定记得git status （实时查看操作状态），如果有修改，阔以git diff查看修改差异。
  然后git add 你的文件，也阔以这样（.代表所有要添加的文件）
  然后git commit -c "提交描述"
5.如果想拉别人分支或主干，你阔以每次先git fetch origin (分支) 在 git merge （other branch），合并完记得看下 第4点。

5（补充）：git pull origin master = git fetch 在 git merge的操作。但是不建议使用git pull
#将远程的origin上的master分支下载下来并存放在本地tmp分支，如果tmp分支不存在则自动创建
#然后将本地仓库跟tmp分支进行对比
#如果没冲突那么将tmp分支合并到当前分支，否则先处理冲突后再合并！最后tmp分支阔以直接删掉也阔以保留！
git fetch origin master:tmp
git diff tmp 
git merge tmp

6.git push origin （master）或你的分支。
7.切换分支 git checkout 某个分支。
8.恢复某个文件 git checkout -- 你的文件
9.删除远程仓库 git remote rm <repository>
10.如果你是在本地创建仓库，则你需要git remote add 【git@192.168.1.100:PHP_Work/TP.git】，你在push
11.git revert HEAD     # 恢复最后一次提交的状态
12.
提交远程分支时，如发现有config-example 之外的配置文件提交到远程，请执行下面命令清除下缓存
git rm -r --cached 文件名
git add .
git commit -m 'update .gitignore'


回退

git reflog
git reset --hard Obfafd

