package com.themagins.filemonitor.utilities;

/**
 * @author Andris Magins
 * @created 08-Mar-20
 **/

import com.themagins.filemonitor.persistance.Inode;

import java.util.List;
public class PathTree {

    public static DirectoryNode createDirectoryTree(final List<Inode> list) {

        DirectoryNode treeRootNode = null;

        for (final Inode inode : list) {

            final String path = inode.getFile_path().startsWith("/") ? inode.getFile_path().substring(1) : inode.getFile_path();

            final String[] pathElements = path.split("/");
            DirectoryNode movingNode = null;

            for (final String pathElement : pathElements) {
                movingNode = new DirectoryNode(movingNode, pathElement,inode.getFile_path());
            }

            if (treeRootNode == null) {
                treeRootNode = movingNode.getRoot();
            } else {
                treeRootNode.merge(movingNode.getRoot());
            }
        }

        return treeRootNode;
    }
}
