import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import git4idea.repo.GitRepositoryManager
import java.io.File
import java.net.URL

class simplePlugin : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project: Project? = e.getData(PlatformDataKeys.PROJECT)
        val file: VirtualFile? = e.getData(CommonDataKeys.VIRTUAL_FILE)

        if (project != null && file != null) {
            val owner = findOwner(project)
            val filePath = findFilePath(project, file)
            val currentBranch = findCurrentBranch(project)
            openGithubLink(owner, project, filePath, currentBranch)
            showStars(owner, project)
        } else {
            Messages.showMessageDialog("There is no open project", "Error", Messages.getErrorIcon())
        }
    }

    private fun findOwner(project: Project): String {
        var owner = ""
        val gitConfigFile = File(project.basePath + "/.git/config")

        gitConfigFile.forEachLine {
            if (it.contains("url")) {
                owner = it.replace("//|:|@".toRegex(), "/")
                    .substringBeforeLast("/")
                    .substringAfterLast("/")
            }
        }

        return owner
    }

    private fun findFilePath(project: Project, file: VirtualFile): String {
        return file.path.substringAfter(project.basePath ?: "")
    }

    private fun findCurrentBranch(project: Project): String {
        var currentBranch = "main"

        val repositories = GitRepositoryManager.getInstance(project).repositories;
        for (repository in repositories) {
            currentBranch = repository.currentBranch.toString().substringAfterLast("/")
        }

        return currentBranch
    }

    private fun openGithubLink(owner: String, project: Project, filePath: String, currentBranch: String) {
        val fileLink = if (filePath == "") "" else "blob/$currentBranch$filePath"

        BrowserUtil.browse("https://github.com/$owner/${project.name}/$fileLink")
    }

    private fun showStars(owner: String, project: Project) {
        val text = URL("https://api.github.com/repos/$owner/${project.name}").readText()
        val node: JsonNode = ObjectMapper().readTree(text)

        Messages.showMessageDialog("The number of stargazers is: ${node.get("stargazers_count")}",
            "Info",  Messages.getInformationIcon())
    }
}