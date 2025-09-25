package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {

    public static Path createTimestampedBackup(Path sourceRoot) throws IOException {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        Path dest = Paths.get("backups","backup-"+ts);
        Files.createDirectories(dest);
        Files.walkFileTree(sourceRoot, new SimpleFileVisitor<>(){
            @Override public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path rel = sourceRoot.relativize(file);
                Path to = dest.resolve(rel);
                Files.createDirectories(to.getParent());
                Files.copy(file, to, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
        return dest;
    }

    public static long computeDirectorySize(Path root) throws IOException {
        final long[] size = {0};
        Files.walkFileTree(root, new SimpleFileVisitor<>(){
            @Override public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                size[0] += attrs.size();
                return FileVisitResult.CONTINUE;
            }
        });
        return size[0];
    }
}
