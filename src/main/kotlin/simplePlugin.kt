import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

class simplePlugin: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project: Project? = e.getData(PlatformDataKeys.PROJECT)
        val file: VirtualFile? = e.getData(CommonDataKeys.VIRTUAL_FILE)

        if (project != null) {
            findOwner(project)
        } else {
            Messages.showMessageDialog("Project is not open yet", "Error",  Messages.getErrorIcon())
        }

        //1. Project == null -> show Error
        //2. Project != null ->
            //a. file == null -> open git repo main page
            //b. file != null ->
                //aa. Find owner
                //bb. Find file path
                //cc. Find current branch
                //dd. Build link
                //ee. Open link

    }

    private fun findOwner(project: Project): String {
        var owner = ""
        val gitConfigFile = File(project.basePath + "/.git/config")

        gitConfigFile.forEachLine {
            if (it.contains("url")) {
                val parts = it.split(":|/".toRegex())
                owner = parts[1]
            }
        }

        return owner
    }
    
}